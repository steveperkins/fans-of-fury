using System.Threading.Tasks;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// A repository for accessing UserData
    /// </summary>
    public interface IUserDataRepository : ITableRepository<UserData>
    {
        /// <summary>
        /// Gets a user by user ID
        /// </summary>
        Task<UserData> Get(string userId);
    }
}
