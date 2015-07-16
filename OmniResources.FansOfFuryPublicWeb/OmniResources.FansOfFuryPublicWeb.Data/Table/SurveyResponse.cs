using System;
using System.ComponentModel.DataAnnotations;
using WindowsAzure.Table.Attributes;

namespace OmniResources.FansOfFuryPublicWeb.Data.Table
{
    /// <summary>
    /// A response to the QR code survey.  One entry in this table will be made for every response, even if the
    /// user modifies an existing response.
    /// </summary>
    public class SurveyResponse
    {
        /// <summary>
        /// Initializes new instances of the SurveyResponse class
        /// </summary>
        public SurveyResponse()
        {
            ResponseTimestamp = DateTime.UtcNow;
        }

        /// <summary>
        /// Gets or sets the ID of the user (from the QR code - SHOULD NOT BE DISPLAYED ANYWHERE ON THE SITE)
        /// </summary>
        [PartitionKey]
        public string UserId { get; set; }

        /// <summary>
        /// Gets the rowkey - the inverse of the ResponseTimestamp ticks to enable sorting by ResponseTimestamp
        /// </summary>
        [RowKey]
        public string RowKey
        {
            get { return (long.MaxValue - ResponseTimestamp.Ticks).ToString(); }
            private set { /* Ignore set */ }
        }

        /// <summary>
        /// Gets or sets the timestamp of the response
        /// </summary>
        public DateTime ResponseTimestamp { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether the user had password protected the user ID at the time of survey entry
        /// </summary>
        public bool Authenticated { get; set; }

        /// <summary>
        /// Gets or sets the first naame of the user
        /// </summary>
        [Required]
        public string FirstName { get; set; }

        /// <summary>
        /// Gets or sets the last name of the user
        /// </summary>
        [Required]
        public string LastName { get; set; }

        /// <summary>
        /// Gets or sets the email address of the user
        /// </summary>
        [EmailAddress]
        public string EmailAddress { get; set; }
    }
}
