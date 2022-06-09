const parkingSpotApi = {
  loginRequest: async (username, password) => {
    const requestBody = JSON.stringify({ username, password });
    const url = process.env.REACT_APP_LOGIN_URI;
    const result = await fetch(
      url,
      {
        method: "POST",
        body: requestBody,
      }
    );
    const response = await result.json();
    return { status: result.status, content: response };
  },
  parkingSpots: async (token) => {
    const myHeader = {
      authorization: token
    }

    const url = process.env.REACT_APP_SPOTS_URI;
    const result = await fetch(
      url,
      {
        method: "GET",
        headers: myHeader,
        mode: "cors",
      }
    );
    console.log("url: " + url);
    const response = await result.json();
    console.log("response: " + response);
    return response;
  }
}

export default parkingSpotApi;
