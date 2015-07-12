using WindowsAzure.Table.Attributes;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table
{
    /// <summary>
    /// Stores information about a user
    /// </summary>
    public class UserData
    {
        /// <summary>
        /// Gets or sets the GUID of the user (from the QR code)
        /// </summary>
        [PartitionKey]
        public string UserGuid { get; set; }

        /// <summary>
        /// Gets or sets the ID of the user in the ASP.NET identity system
        /// </summary>
        public string ApplicationUserId { get; set; }
    }
}
