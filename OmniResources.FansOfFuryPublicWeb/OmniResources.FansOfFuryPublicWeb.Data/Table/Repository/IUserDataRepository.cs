using System.Collections.Generic;
using System.Threading.Tasks;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// A repository for accessing UserData
    /// </summary>
    public interface IUserDataRepository : ITableRepository<UserData>
    {
        /// <summary>
        /// Returns all registered users in the system
        /// </summary>
        Task<List<UserData>> Get();

        /// <summary>
        /// Gets a user by user ID
        /// </summary>
        Task<UserData> Get(string userId);
    }
}
