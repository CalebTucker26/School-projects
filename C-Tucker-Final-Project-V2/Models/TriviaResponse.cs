namespace C_Tucker_Final_Project_V2.Models
{
    public class TriviaResponse
    {
        public int response_code { get; set; }
        public List<TriviaQuestion> results { get; set; }
    }
}
