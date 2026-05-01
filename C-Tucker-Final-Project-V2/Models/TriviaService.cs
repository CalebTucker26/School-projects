using System.Text.Json;
using C_Tucker_Final_Project_V2.Models;
namespace C_Tucker_Final_Project_V2
{
    public class TriviaService
    {
        private readonly HttpClient _http;

        public TriviaService(HttpClient http)
        {
            _http = http;
            _http.BaseAddress = new Uri("https://opentdb.com/");
        }

        public async Task<List<TriviaQuestion>> GetQuestionAsync(int amount)
        {
            var response = await _http.GetStringAsync(
                $"https://opentdb.com/api.php?amount=10&category=20&difficulty=medium&type=multiple"
            );

            var data = JsonSerializer.Deserialize<TriviaResponse>(response);

            if (data?.results == null)
                return new List<TriviaQuestion>();

            foreach (var item in data.results)
            {
                item.Answers = item.incorrect_answers
                    .Append(item.correct_answer)
                    .OrderBy(x => Guid.NewGuid())
                    .ToList();
            }

            return data.results;
        }
    }
}
