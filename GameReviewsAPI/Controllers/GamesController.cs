using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using GameReviewsAPI;
using GameReviewsAPI.Data;

namespace GameReviewsAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GamesController : ControllerBase
    {
        private readonly GameReviewsAPIContext _context;

        public GamesController(GameReviewsAPIContext context)
        {
            _context = context;
        }

        // GET: api/Games
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Game>>> GetGame()
        {
          if (_context.Game == null)
          {
              return NotFound();
          }
            return await _context.Game.ToListAsync();
        }

        // GET: api/Games/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Game>> GetGame(int id)
        {
          if (_context.Game == null)
          {
              return NotFound();
          }
            var game = await _context.Game.FindAsync(id);

            if (game == null)
            {
                return NotFound();
            }

            return game;
        }


        // POST: api/Games
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Game>> PostGame(Game game)
        {
          if (_context.Game == null)
          {
              return Problem("Entity set 'GameReviewsAPIContext.Game'  is null.");
          }
            _context.Game.Add(game);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGame", new { id = game.GameID }, game);
        }

        // DELETE: api/Games/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGame(int id)
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var game = await _context.Game.FindAsync(id);
            if (game == null)
            {
                return NotFound();
            }

            _context.Game.Remove(game);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        // GET: api/Games/5
        [HttpGet("search")]
        public async Task<ActionResult<IEnumerable<Game>>> SearchGames(
    string? title, string? platform, double? stars, string? genre)
        {
            var query = _context.Game.Include(g => g.Reviews).AsQueryable();

            if (!string.IsNullOrEmpty(title))
                query = query.Where(g => g.GameTitle.Contains(title));

            if (!string.IsNullOrEmpty(platform))
                query = query.Where(g => g.Platforms.Contains(platform));

            if (stars.HasValue)
                query = query.Where(g => g.Reviews.Any() && g.Reviews.Average(r => r.Stars) >= stars);

            if (!string.IsNullOrEmpty(genre))
                query = query.Where(g => g.Genre.Contains(genre));

            return await query.ToListAsync();
        }


        private bool GameExists(int id)
        {
            return (_context.Game?.Any(e => e.GameID == id)).GetValueOrDefault();
        }
    }
}
