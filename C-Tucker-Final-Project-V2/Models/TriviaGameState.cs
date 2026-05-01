using AspNetCoreGeneratedDocument;
namespace C_Tucker_Final_Project_V2.Models
{
    public class TriviaGameState
    {
        public int CurrentQuestionIndex { get; set; } = 0;
        public int score { get; set; } = 0;
        public List<TriviaQuestion> Questions { get; set; } = new();
    }
}
