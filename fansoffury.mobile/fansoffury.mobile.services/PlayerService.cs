using System;
using fansoffury.mobile.domain;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Net;
using System.IO;
using System.Text;
using System.Linq;

namespace fansoffury.mobile.services
{
	public class PlayerService : IPlayerService
	{
		#region IPlayerService implementation

		public Player GetPlayer (string id)
		{
			WebRequest request = WebRequest.Create (string.Format ("{0}api/player/{1}", AppSettings.BaseURL, id));
			using (var response = request.GetResponse ()) {
				using (var stream = response.GetResponseStream ()) {
					using (var reader = new StreamReader (stream)) {
						var result = JsonConvert.DeserializeObject<JsonResponse<Player>> (reader.ReadToEnd ());
						if (result.Errors != null && result.Errors.Any ()) {
							StringBuilder sb = new StringBuilder ();
							throw new Exception (result.Errors.Aggregate (sb, (x, y) => x.AppendLine (y)).ToString());
						}
						return result.Object;
					}
				}
			}
		}

		public Player AssignPlayer (fansoffury.mobile.domain.JsonHeadset headset)
		{
			WebRequest request = WebRequest.Create (string.Format ("{0}api/player/", AppSettings.BaseURL));
			request.Method = "PUT";
			request.ContentType = "application/json";
			using (var requestStream = request.GetRequestStream ()) {
				var headsetString = JsonConvert.SerializeObject (headset);
				var bytes = new ASCIIEncoding ().GetBytes (headsetString);
				requestStream.Write (bytes, 0, bytes.Length);
				requestStream.Close ();
			}
			using (var response = request.GetResponse ()) {
				using (var stream = response.GetResponseStream ()) {
					using (var reader = new StreamReader (stream)) {
						var result = JsonConvert.DeserializeObject<JsonResponse<Player>> (reader.ReadToEnd ());
						if (result.Errors != null && result.Errors.Any ()) {
							StringBuilder sb = new StringBuilder ();
							throw new Exception (result.Errors.Aggregate (sb, (x, y) => x.AppendLine (y)).ToString());
						}
						return result.Object;
					}
				}
			}
		}

		#endregion
	}
}

