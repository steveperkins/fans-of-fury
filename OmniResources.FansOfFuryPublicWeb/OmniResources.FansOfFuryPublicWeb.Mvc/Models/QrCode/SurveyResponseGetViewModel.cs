namespace OmniResources.FansOfFuryPublicWeb.Mvc.Models.QrCode
{
    public class SurveyResponseGetViewModel : SurveyResponsePostViewModel
    {
        public enum UserState
        {
            New,
            UserDataExists,
            Authenticated
        }

        /// <summary>
        /// Gets or sets a value indicating whether someone has claimed that QR code yet
        /// </summary>
        public UserState State { get; set; }
    }
}