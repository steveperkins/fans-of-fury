using System.ComponentModel.DataAnnotations;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Models.QrCode
{
    public class SurveyResponsePostViewModel
    {
        /// <summary>
        /// Gets or sets the ID of the user
        /// </summary>
        public string UserId { get; set; }

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

        /// <summary>
        /// Gets or sets the name to display for the user on the scoreboard
        /// </summary>
        public string ScoreboardName { get; set; }

        /// <summary>
        /// Gets or sets a password for the user that they must enter if they want to have their name on the scoreboard
        /// </summary>
        public string Password { get; set; }
    }
}