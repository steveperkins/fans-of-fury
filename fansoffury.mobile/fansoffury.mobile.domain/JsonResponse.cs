using System;
using System.Collections.Generic;

namespace fansoffury.mobile.domain
{
	public class JsonResponse<T>
	{
		public string Status { get; set; }

		public string Message { get; set; }

		public List<string> Errors { get; set; }

		public T Object { get; set; }
	}
}

