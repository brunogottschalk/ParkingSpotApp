import { useNavigate, useLocation } from 'react-router-dom';
import { useContext, useState } from 'react';
import CoreContext from '../contextApi/CoreContext';
import moment from 'moment';
import vipImage from '../static/resources/cartao-vip.png'
import "../static/styles/newParkingPage.css"

function NewParkingComponent() {
  const location = useLocation();
  const navigate = useNavigate();

  const { spots, requestNewParkingSpot, parkingResponseMessage, getSpots } = useContext(CoreContext);

  const currentSpotId = location.pathname.split("/")[2];
  const currentDate = new moment().format("YYYY-MM-DD HH:mm:ss");

  const currentSpot = spots.find((spot) => spot.id === parseInt(currentSpotId));

  const [estimatedValue, setEstimatedValue] = useState(0.00);
  const [departureDate, setDepartureDate] = useState(undefined);

  function valueCalculator(event) {
    setDepartureDate(moment(event.target.value).format("YYYY-MM-DD HH:mm:ss"));
    const interval = moment.duration(moment(currentDate).diff(event.target.value, "YYYY-MM-DD HH:mm:ss")).as("hours") * -1;
    console.log(Math.ceil(interval));
    setEstimatedValue(currentSpot.spotType.value * Math.ceil(interval))
  }

  async function newParkingRequest() {
    await requestNewParkingSpot(currentSpot.id, departureDate);
    await getSpots();
  }

  return (
    <div id="parkingPageContainer">
      <div id="parkingSpotMenuBar">
        <button onClick={ () => navigate("/home") }>Back to Home Page</button>
      </div>
      <form id="parkingPageForm">
        <label htmlFor="currentDate">
          <span>Entry-Date: </span>
          <input type="datetime-local" id="currentDate" value={ currentDate } disabled="true" />
        </label>
        <label htmlFor="departureDate">
          <span>Departure-Date: </span>
          <input type="datetime-local"  id="departureDate" min={ currentDate } onChange={ valueCalculator }/>
        </label>
        
          <div id="valuePerHour">
            <span>{ currentSpot.spotType.value } USD per Hour</span>
            { currentSpot.spotType.type === "vip" && <img id="spotTypeImage" src={ vipImage } alt="vip" />}
          </div>
          <div id="estimatedValue">Estimated Value: { estimatedValue } USD</div>

        <button id="newParkingRequestButton" onClick={ newParkingRequest }>Confirm new parking request</button>

        { parkingResponseMessage && <h2>{parkingResponseMessage.content.message}</h2>}
        { parkingResponseMessage && parkingResponseMessage.status === 201 && navigate("/home") }
      </form>
    </div>
  );

}

export default NewParkingComponent;
