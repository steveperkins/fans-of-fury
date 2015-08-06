using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using OmniResources.FansOfFuryPublicWeb.Data.Table;
using OmniResources.FansOfFuryPublicWeb.Data.Table.Repository;
using OmniResources.FansOfFuryPublicWeb.Mvc.Models.Sync;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Controllers
{
    public class SyncController : ApiController
    {
        private readonly IUserDataRepository _userDataRepo;

        public SyncController(IUserDataRepository userDataRepo)
        {
            _userDataRepo = userDataRepo;
        }

        public async Task<IEnumerable<UserData>> Post(IEnumerable<ScoreData> scores, string p)
        {
            if (p != ConfigurationManager.AppSettings["SyncPass"])
                return null;

            var users = (await _userDataRepo.Get()).ToDictionary(x => x.UserId, x => x);
            var usersToUpdate = new HashSet<UserData>();

            foreach (var score in scores)
            {
                UserData user;

                if (users.ContainsKey(score.UserId))
                {
                    user = users[score.UserId];
                }
                else
                {
                    user = new UserData { UserId = score.UserId };
                    users.Add(score.UserId, user);
                    usersToUpdate.Add(user);
                }

                if (user.Score != score.Score)
                {
                    user.Score = score.Score;
                    usersToUpdate.Add(user);
                }
            }

            if (usersToUpdate.Any())
                await _userDataRepo.SaveBatch(usersToUpdate);

            return users.Values;
        }
    }
}