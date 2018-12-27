package net.runelite.client.plugins.timetracking;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLiteProperties;
import net.runelite.http.api.RuneLiteAPI;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class Notifier
{
	private static String notifierUrl = new RuneLiteProperties().getOsrsNotifier();

	public static void updateNotifier(JsonObject body, String path)
	{
		log.debug("updateNotifier {}", body);
		Request request = new Request.Builder()
				.url(notifierUrl + path)
				.put(RequestBody.create(JSON, body.toString()))
				.build();
		send(request);
	}

	private static final MediaType JSON = MediaType.parse("application/json");

	public static void updateNumber(String username, String number)
	{
		JsonObject body = new JsonObject();
		body.addProperty("username", username);
		body.addProperty("number", number);
		log.debug("updateNumber {}", body);
		Request request = new Request.Builder()
				.url(notifierUrl + "/users")
				.put(RequestBody.create(JSON, body.toString()))
				.build();
		send(request);
	}

	private static void send(Request request)
	{
		RuneLiteAPI.CLIENT.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				log.error("failed to send farming update {} {}", e.toString(), request.toString());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				if (response.isSuccessful())
				{
					response.close();
					return;
				}

				log.error("got unknown response {}", String.valueOf(response.body().string()));
				response.close();
			}
		});
	}
}
