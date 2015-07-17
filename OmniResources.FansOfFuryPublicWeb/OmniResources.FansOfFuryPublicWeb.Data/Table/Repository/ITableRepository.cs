using System.Collections.Generic;
using System.Threading.Tasks;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table.Repository
{
    /// <summary>
    /// Basic table repository interface
    /// </summary>
    public interface ITableRepository<T> where T : class, new()
    {
        /// <summary>
        /// Deletes the given entity
        /// </summary>
        Task Delete(T item);

        /// <summary>
        /// Deletes all the given entities
        /// </summary>
        Task DeleteBatch(IEnumerable<T> items);

        /// <summary>
        /// Inserts or replaces the given entity
        /// </summary>
        Task Save(T item);

        /// <summary>
        /// Inserts or replaces all of the given entities
        /// </summary>
        Task SaveBatch(IEnumerable<T> items);
    }
}
