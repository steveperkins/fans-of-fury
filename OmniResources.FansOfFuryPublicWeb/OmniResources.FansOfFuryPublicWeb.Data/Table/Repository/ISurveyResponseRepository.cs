using System.Collections.Generic;
using System.Threading.Tasks;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// Repository for accessing survey responses
    /// </summary>
    public interface ISurveyResponseRepository : ITableRepository<SurveyResponse>
    {
        /// <summary>
        /// Gets the most recent survey response for the given user ID
        /// </summary>
        Task<SurveyResponse> GetCurrent(string userId);

        /// <summary>
        /// Gets all SurveyResponses by user ID
        /// </summary>
        Task<List<SurveyResponse>> Get(string userId);
    }
}
