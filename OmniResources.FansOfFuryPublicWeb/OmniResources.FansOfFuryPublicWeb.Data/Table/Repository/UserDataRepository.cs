﻿using System.Collections.Generic;
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
        /// Returns all registered users in the system
        /// </summary>
        public async Task<List<UserData>> Get()
        {
            return await Table.ToListAsync();
        }

        /// <summary>
        /// Gets a user by user ID
        /// </summary>
        public async Task<UserData> Get(string userId)
        {
            return await Table.SingleOrDefaultAsync(x => x.UserId == userId);
        }
    }
}
