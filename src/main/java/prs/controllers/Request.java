package prs.controllers;

import java.io.IOException;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import prs.models.AccountModel;
import prs.models.DoctorModel;

public class Request {
	Header[] headers;
	String Url = "http://localhost:8080";
	Gson gson;
	HttpClient httpClient = HttpClientBuilder.create().build();

	public Request() {
		headers = new Header[2];
		headers[0] = new BasicHeader("Content-Type", "application/json");
		headers[1] = new BasicHeader("Authorization", "Basic cHJzOnNlY3JldA==");
		gson = new Gson();
	}

	public void Get() {

	}

	public void Post() {

	}

	public void createDoctorAccount(String trace, AccountModel account, DoctorModel doctor) {
		Url += trace;
		HttpPost post = new HttpPost(Url);
		StringEntity postingString = null;

		try {
			postingString = new StringEntity(
					"{\"doctor\":" + gson.toJson(doctor) + ",\"account\":" + gson.toJson(account) + "}");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		post.setHeaders(headers);
		post.setEntity(postingString);

		HttpResponse response = null;
		String responseString = null;
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.getStatusLine().getStatusCode() == 409) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Account");
			alert.setHeaderText("Problem with create account, account alredy exist");
			alert.setContentText(
					"The data that you typed can exist in database, contact with administrator if it's mistake");
			alert.showAndWait();
		} else if (response.getStatusLine().getStatusCode() == 201) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Account");
			alert.setHeaderText("Account created");
			alert.setContentText("Account for email: " + account.getUsername() + " created");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Account");
			alert.setHeaderText("Try again or later");
			alert.setContentText("The data that you typed can be wrong\nor you have problem with connection");
			alert.showAndWait();
		}

	}

	public String getToken(String username, String password) {

		Url += "/token?grant_type=password&username=" + username + "&" + "password=" + password;
		HttpPost post = new HttpPost(Url);
		StringEntity postingString = null;
		String token = null;

		try {
			postingString = new StringEntity("token");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		post.setHeaders(headers);
		post.setEntity(postingString);

		HttpResponse response = null;
		String responseString = null;
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			responseString = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.getStatusLine().getStatusCode() != 200) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Sign In");
			alert.setHeaderText("Data typed is wrong, try again now or later");
			alert.setContentText("The data that you typed can be wrong\nor you have problem with connection");
			token = null;
			alert.showAndWait();
		} else {
			String[] result = responseString.split(",");
			token = result[0].replaceAll("\\\"|\\{", "");
			token = token.replace(":", "=");
		}
		return token;
	}
}
