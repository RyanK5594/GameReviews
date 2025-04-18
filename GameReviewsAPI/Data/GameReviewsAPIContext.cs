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

        public DbSet<Game> Game { get; set; } = default!;
        public DbSet<Review> Review { get; set; } = default!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Review>()
                .HasOne<Game>()
                .WithMany(g => g.Reviews)
                .HasForeignKey(r => r.GameID)
                .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
