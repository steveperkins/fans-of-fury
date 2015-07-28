using System;
using fansoffury.mobile.domain;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Net;
using System.IO;
using System.Text;

namespace fansoffury.mobile.services
{
	public class PlayerService : IPlayerService
	{
		#region IPlayerService implementation

		public Player GetPlayer (string id)
		{
			WebRequest request = WebRequest.Create (string.Format ("{0}api/player/{1}", AppSettings.BaseURL, id));
			var response = request.GetResponse ();
			var stream = response.GetResponseStream ();
			var reader = new StreamReader (stream);
			return JsonConvert.DeserializeObject<JsonResponse<Player>> (reader.ReadToEnd ()).Object;
		}

		public Player AssignPlayer (fansoffury.mobile.domain.JsonHeadset headset)
		{
			WebRequest request = WebRequest.Create (string.Format ("{0}api/player/", AppSettings.BaseURL));
			request.Method = "PUT";
			request.ContentType = "application/json";
			var requestStream = request.GetRequestStream ();
			var headsetString = JsonConvert.SerializeObject (headset);
			var bytes = new ASCIIEncoding ().GetBytes (headsetString);
			requestStream.Write (bytes, 0, bytes.Length);
			requestStream.Close ();
			var response = request.GetResponse ();
			var stream = response.GetResponseStream ();
			var reader = new StreamReader (stream);
			return JsonConvert.DeserializeObject<JsonResponse<Player>> (reader.ReadToEnd ()).Object;
		}

		#endregion
	}
}

