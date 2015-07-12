using System.Security.Claims;
using System.Threading.Tasks;
using AccidentalFish.AspNet.Identity.Azure;
using Microsoft.AspNet.Identity;

namespace OmniResources.FansOfFuryPublicWeb.Mvc.Models
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : TableUser
    {
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager)
        {
            // Note the authenticationType must match the one defined in CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, DefaultAuthenticationTypes.ApplicationCookie);

            // Add custom user claims here
            return userIdentity;
        }

        /// <summary>
        /// Gets or sets the GUID of the user (comes from the id on the QR Code used to register the user)
        /// </summary>
        public string UserGuid { get; set; }
    }
}