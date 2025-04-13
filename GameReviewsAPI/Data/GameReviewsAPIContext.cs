using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using GameReviewsAPI;

namespace GameReviewsAPI.Data
{
    public class GameReviewsAPIContext : DbContext
    {
        public GameReviewsAPIContext (DbContextOptions<GameReviewsAPIContext> options)
            : base(options)
        {
        }

        public DbSet<GameReviewsAPI.Game> Game { get; set; } = default!;

        public DbSet<GameReviewsAPI.Review> Review { get; set; } = default!;
    }
}
