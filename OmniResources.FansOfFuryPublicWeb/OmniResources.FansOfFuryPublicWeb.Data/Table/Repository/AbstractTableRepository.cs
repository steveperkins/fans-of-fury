using System.Collections.Generic;
using System.Threading.Tasks;
using WindowsAzure.Table;
using Microsoft.WindowsAzure.Storage;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// Abstract base class for all table repositories
    /// </summary>
    public abstract class AbstractTableRepository<T> where T : class, new()
    {
        // ReSharper disable once StaticMemberInGenericType
        private static readonly HashSet<string> CreatedTables = new HashSet<string>();

        /// <summary>
        /// Initializes a new instance of the AbstractTableRepository class
        /// </summary>
        protected AbstractTableRepository(string connectionString)
            : this(typeof(T).Name, connectionString)
        {
        }


        /// <summary>
        /// Initializes a new instance of the AbstractTableRepository class
        /// </summary>
        protected AbstractTableRepository(string tableName, string connectionString)
        {
            var tableClient = CloudStorageAccount.Parse(connectionString).CreateCloudTableClient();

            lock (CreatedTables)
            {
                if (!CreatedTables.Contains(tableName))
                {
                    tableClient.GetTableReference(tableName).CreateIfNotExists();
                    CreatedTables.Add(tableName);
                }
            }

            Table = new TableSet<T>(tableClient);
        }

        /// <summary>
        /// Gets the table referred to by the repository
        /// </summary>
        protected TableSet<T> Table { get; private set; }

        /// <summary>
        /// Deletes the given entity
        /// </summary>
        public async virtual Task Delete(T item)
        {
            await Table.RemoveAsync(item);
        }

        /// <summary>
        /// Deletes all the given entities
        /// </summary>
        public async virtual Task DeleteBatch(IEnumerable<T> items)
        {
            await Table.RemoveAsync(items);
        }

        /// <summary>
        /// Inserts or replaces the given entity
        /// </summary>
        public async virtual Task Save(T item)
        {
            await Table.AddOrUpdateAsync(item);
        }

        /// <summary>
        /// Inserts or replaces all of the given entities
        /// </summary>
        public async virtual Task SaveBatch(IEnumerable<T> items)
        {
            await Table.AddOrUpdateAsync(items);
        }
    }
}
