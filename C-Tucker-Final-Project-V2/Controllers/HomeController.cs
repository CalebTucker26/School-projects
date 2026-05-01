using C_Tucker_Final_Project_V2.Models;
using C_Tucker_Final_Project_V2.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json;
using C_Tucker_Final_Project_V2;

namespace C_Tucker_Final_Project.Controllers
{
    public class HomeController : Controller
    {
        private readonly TriviaService _triviaService;
        private readonly ApplicationDbContext _context;

        public HomeController(TriviaService triviaService, ApplicationDbContext context)
        {
            _triviaService = triviaService;
            _context = context;
        }

        public IActionResult Index()
        {
            return View();
        }

        private void SaveGame(TriviaGameState game)
        {
            HttpContext.Session.SetString("GameState", JsonSerializer.Serialize(game));
        }

        private TriviaGameState LoadGame()
        {
            var data = HttpContext.Session.GetString("GameState");

            return data == null
                ? new TriviaGameState()
                : JsonSerializer.Deserialize<TriviaGameState>(data);
        }


        public async Task<IActionResult> StartGame()
        {
            var questions = await _triviaService.GetQuestionAsync(10);

            var game = new TriviaGameState()
            {
                Questions = questions,
                CurrentQuestionIndex = 0,
                score = 0
            };

            SaveGame(game);

            return RedirectToAction("Question");
        }

        public IActionResult Question()
        {
            var game = LoadGame();

            if (game == null || game.Questions == null || game.Questions.Count == 0)
            {
                return RedirectToAction("StartGame");
            }

            // if finished, go to results
            if (game.CurrentQuestionIndex >= game.Questions.Count)
            {
                
                return RedirectToAction("Results");
            }

            // get ONLY current question
            var question = game.Questions[game.CurrentQuestionIndex];

            return View(question);
        }


        [HttpPost]
        public IActionResult SubmitAnswer(string selectedAnswer)
        {
            var game = LoadGame();

            if (game == null || game.Questions == null || game.Questions.Count == 0)
            {
                return RedirectToAction("StartGame");
            }

            // ✅ ADD THIS CHECK FIRST
            if (game.CurrentQuestionIndex >= game.Questions.Count)
            {
                return RedirectToAction("Results");
            }

            var question = game.Questions[game.CurrentQuestionIndex];

            if (!string.IsNullOrEmpty(selectedAnswer) &&
                selectedAnswer == question.correct_answer)
            {
                game.score += 10;
            }

            game.CurrentQuestionIndex++;

            SaveGame(game);

            // this one is fine (post-increment check)
            if (game.CurrentQuestionIndex >= game.Questions.Count)
            {
                return RedirectToAction("Results");
            }

            return RedirectToAction("Question");
        }


        public IActionResult Results()
        {
            var game = LoadGame();

            if (game == null)
            {
                return RedirectToAction("Index");
            }
            var score = new Score
            {
                UserId = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value,
                Points = game.score,
                DatePlayed = DateTime.Now
            };

            _context.Scores.Add(score);
            _context.SaveChanges();

            return View(game);
        }

        public IActionResult Leaderboard()
        {
            var scores = _context.Scores
                .Include(s => s.User)
                .OrderByDescending(s => s.Points)
                .Select(s => new LeaderboardViewModel
                {
                    Email = s.User.Email,
                    Score = s.Points
                })
                .ToList();

            return View(scores);
        }
    }
}