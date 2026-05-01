namespace C_Tucker_Final_Project_V2.Models
{
    public class TriviaQuestion
    {
        public string question { get; set; }
        public string correct_answer { get; set; }
        public List<string> incorrect_answers { get; set; }

        public List<string> Answers { get; set; }
    }
}
