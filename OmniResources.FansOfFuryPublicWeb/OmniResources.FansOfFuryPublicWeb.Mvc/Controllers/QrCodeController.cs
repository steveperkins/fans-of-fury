using System.Threading.Tasks;
using System.Web.Mvc;
using AutoMapper;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;
using OmniResources.FansOfFuryPublicWeb.Mvc.Models.QrCode;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class QrCodeController : BaseController
    {
        private readonly IUserDataRepository _userDataRepo;
        private readonly ISurveyResponseRepository _surveyResponseRepo;

        public QrCodeController(IUserDataRepository userDataRepo, ISurveyResponseRepository surveyResponseRepo)
        {
            _userDataRepo = userDataRepo;
            _surveyResponseRepo = surveyResponseRepo;
        }

        public async Task<ActionResult> Index(string id)
        {
            var model = new SurveyResponseGetViewModel();

            // See if someone's already filled out a registration for that id...
            var existingUser = await _userDataRepo.Get(id);

            if (existingUser != null)
            {
                // If the user is logged in and the user id matches, pre-populate the form...
                if (UserId == existingUser.UserId && Authenticated)
                {
                    var currentSurvey = await _surveyResponseRepo.GetCurrent(id);
                    model = Mapper.Map<SurveyResponseGetViewModel>(existingUser);
                    Mapper.Map(currentSurvey, model);

                    model.State = SurveyResponseGetViewModel.UserState.Authenticated;
                }
                else
                {
                    model.State = SurveyResponseGetViewModel.UserState.UserDataExists;
                }
            }

            return View(model);
        }
    }
}