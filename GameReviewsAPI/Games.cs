using System.Text.Json.Serialization;

namespace GameReviewsAPI
{
    public class Game
    {
        public int GameID { get; set; }
        public string GameTitle { get; set; }
        public string GameCover { get; set; }
        public string Description { get; set; }
        public double AverageStars { get; set; }
        public string Genre { get; set; }
        public string Devloper { get; set; }
        public string Publisher { get; set; }
        public DateTime ReleaseDate { get; set;}
        public string Platforms { get; set; }
        public bool Multiplayer { get; set; }
        public string SocialMedia { get; set; }
        public string ControllerType { get; set; }
        public string Languages { get; set; }
        public string Requirements { get; set; }
        public string GameplayImages { get; set; }
        public double Price { get; set; }

        public List<Review> Reviews { get; set; }
    }

    public class Review
    {
        public int ReviewID { get; set; }
        public string ReviewText { get; set; }
        public string User { get; set; }
        public double Stars { get; set; }
        public DateTime DateOfReview { get; set; }
        public int GameID { get; set; }
    }
}
