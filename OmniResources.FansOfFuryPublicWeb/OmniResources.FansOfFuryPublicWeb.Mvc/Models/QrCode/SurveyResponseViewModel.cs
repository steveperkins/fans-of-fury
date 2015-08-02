using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using AutoMapper;
using Foolproof;
using OmniResources.FansOfFuryPublicWeb.Data.Table;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Models.QrCode
{
    public class SurveyResponseViewModel
    {
        static SurveyResponseViewModel()
        {
            Mapper.CreateMap<SurveyResponseViewModel, SurveyResponse>().ReverseMap();
            Mapper.CreateMap<SurveyResponseViewModel, UserData>().ReverseMap();
        }

        /// <summary>
        /// Gets or sets the first naame of the user
        /// </summary>
        [Required, DisplayName("First Name")]
        public string FirstName { get; set; }

        /// <summary>
        /// Gets or sets the last name of the user
        /// </summary>
        [Required, DisplayName("Last Name")]
        public string LastName { get; set; }

        /// <summary>
        /// Gets or sets the email address of the user
        /// </summary>
        [Required, EmailAddress, DisplayName("Email Address")]
        public string EmailAddress { get; set; }

        /// <summary>
        /// Gets or sets the name to display for the user on the scoreboard
        /// </summary>
        [DisplayName("Scoreboard Name")]
        public string ScoreboardName { get; set; }

        /// <summary>
        /// Gets or sets a password for the user that they must enter if they want to have their name on the scoreboard
        /// </summary>
        [RequiredIfNotEmpty("ScoreboardName")]
        public string Password { get; set; }

        [RequiredIfNotEmpty("ScoreboardName"), Compare("Password"), DisplayName("Confirm Password")]
        public string ConfirmPassword { get; set; }
    }
}