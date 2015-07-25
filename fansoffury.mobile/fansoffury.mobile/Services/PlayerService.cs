using System;
using fansoffury.mobile.domain;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Net.Http;
using System.Net.Http.Headers;

namespace fansoffury.mobile.services
{
	public class PlayerService : IPlayerService
	{
		string baseUrl = "http://localhost:8080/";
		#region IPlayerService implementation

		public async Task<Player> GetPlayer (string id)
		{
			using (var client = new HttpClient ()) {
				client.BaseAddress = new Uri (baseUrl);
				client.DefaultRequestHeaders.Accept.Clear ();
				client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

				var response = await client.GetAsync(string.Format("api/player/{0}", id));
				if (response.IsSuccessStatusCode)
				{
					var result = await response.Content.ReadAsStringAsync ();
					return JsonConvert.DeserializeObject<JsonResponse<Player>> (result).Object;
				}
			}
			return null;
		}

		public void AssignPlayer (fansoffury.mobile.domain.Player player)
		{
			Console.WriteLine ("Assign Player");
		}

		#endregion
	}
}

