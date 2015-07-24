using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WindowsAzure.Table.Extensions;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// Repository for accessing survey responses
    /// </summary>
    public class SurveyResponseRepository : AbstractTableRepository<SurveyResponse>, ISurveyResponseRepository
    {
        /// <summary>
        /// Initializes a new instance of the SurveyResponseRepository class
        /// </summary>
        public SurveyResponseRepository(string connectionString)
            : base(connectionString)
        {
        }

        /// <summary>
        /// Gets the most recent survey response for the given user ID
        /// </summary>
        public async Task<SurveyResponse> GetCurrent(string userId)
        {
            return await Table.FirstOrDefaultAsync(x => x.UserId == userId && x.Authenticated);
        }

        /// <summary>
        /// Gets all SurveyResponses by user ID
        /// </summary>
        public async Task<List<SurveyResponse>> Get(string userId)
        {
            return await Table.Where(x => x.UserId == userId).ToListAsync();
        }
    }
}
