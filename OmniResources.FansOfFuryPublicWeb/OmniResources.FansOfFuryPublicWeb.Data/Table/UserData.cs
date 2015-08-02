using WindowsAzure.Table.Attributes;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table
{
    /// <summary>
    /// Stores information about a user
    /// </summary>
    public class UserData
    {
        /// <summary>
        /// Gets or sets the ID of the user (from the QR code - SHOULD NOT BE DISPLAYED ANYWHERE ON THE SITE)
        /// </summary>
        [PartitionKey]
        public string UserId { get; set; }

        /// <summary>
        /// Gets or sets the name to display for the user on the scoreboard
        /// </summary>
        public string ScoreboardName { get; set; }

        /// <summary>
        /// Gets or sets the user's score
        /// </summary>
        public int Score { get; set; }

        /// <summary>
        /// Gets or sets a (BCrypt encrypted) password for the user that they must enter if they want to have their name on the scoreboard
        /// </summary>
        public string HashedPassword { get; set; }
    }
}
