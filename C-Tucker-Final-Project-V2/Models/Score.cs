namespace C_Tucker_Final_Project_V2.Models
{
    public class Score
    {
        public int Id { get; set; }
        public string UserId { get; set; }
        public User User { get; set; }
        public int Points { get; set; }
        public DateTime DatePlayed { get; set; }
    }
}
