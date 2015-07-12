using System.Threading.Tasks;
using WindowsAzure.Table.Extensions;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// A repository for accessing UserData
    /// </summary>
    public class UserDataRepository : AbstractTableRepository<UserData>, IUserDataRepository
    {
        /// <summary>
        /// Initializes a new instance of the UserDataRepository class
        /// </summary>
        public UserDataRepository(string connectionString)
            : base(connectionString)
        {
        }

        /// <summary>
        /// Gets a user by user GUID
        /// </summary>
        public async Task<UserData> Get(string userGuid)
        {
            return await Table.SingleOrDefaultAsync(x => x.UserGuid == userGuid);
        }
    }
}
