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
    const response = await result.json();
    return response;
  },
  newParkingRequest: async (spotId, departureDate, token) => {
    const requestBody = {
      spotId, departureDate
    }

    const myHeaders = {
      authorization: token,
    }

    myHeaders["Content-Type"] = "application/json"

    const url = process.env.REACT_APP_NEW_PARKING_REQUEST_URI;

    const result = await fetch(
      url,
      {
        method: "POST",
        headers: myHeaders,
        body: JSON.stringify(requestBody),
        
      }
    );
    const response = await result.json();
    return {
      status: result.status,
      content: response
    }
  },
  getHistories: async (token) => {
    const myHeaders = {
      authorization: token,
    }

    const url = process.env.REACT_APP_HISTORIES_URI

    const result = await fetch(
      url,
      {
        method: "GET",
        headers: myHeaders,
        mode: "cors"
      }
    );

    const response = await result.json();

    return {
      status: result.status,
      content: response,
    }
  },
  checkPayments: async (token) => {
    const myHeader = {
      authorization: token
    };
  
    const url = process.env.REACT_APP_PARKING_COMPLETE_URI

    const result = await fetch(
      url,
      {
        method: "GET",
        mode: "cors",
        headers: myHeader,
      }
    );

    const response = await result.json();

    return {
      status: result.status,
      content: response,
    }
  },
  completePayment: async (value, token) => {
    const myHeader = {
      authorization: token,
    };

    myHeader["Content-Type"] = "application/json"

    const requestBody = {
      value
    };

    const url = process.env.REACT_APP_PARKING_COMPLETE_URI;

    const result = await fetch(
      url,
      {
        method: "POST",
        mode: "cors",
        headers: myHeader,
        body: JSON.stringify(requestBody)
      }
    );

    const response = await result.json();

    return {
      status: result.status,
      content: response,
    }
  }
}

export default parkingSpotApi;
