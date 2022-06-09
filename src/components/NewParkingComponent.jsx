import { useLocation } from 'react-router-dom';
import { useContext, useState } from 'react';
import CoreContext from '../contextApi/CoreContext';
import moment from 'moment';

function NewParkingComponent() {
  const location = useLocation();
  const { spots } = useContext(CoreContext);
  const currentSpotId = location.pathname.split("/")[2];
  const currentDate = new moment().format("YYYY-MM-DD HH:mm:ss");

  const currentSpot = spots.find((spot) => spot.id === parseInt(currentSpotId));
  const [estimatedValue, setEstimatedValue] = useState(0.00);

  function valueCalculator(event) {
    const departureDate = event.target.value;
    console.log(currentDate);
    const interval = moment.duration(moment(currentDate).diff(departureDate, "YYYY-MM-DD HH:mm:ss")).as("hours") * -1;
    console.log(Math.ceil(interval));
    setEstimatedValue(currentSpot.spotType.value * Math.ceil(interval))
  }

  return (
    <div>
      <form>
        <label htmlFor="currentDate">
          <span>Entry-Date: </span>
          <input type="datetime-local" id="currentDate" value={ currentDate } disabled="true" />
        </label>
        <label htmlFor="departureDate">
          <span>Departure-Date: </span>
          <input type="datetime-local"  id="departureDate" min={ currentDate } onChange={ valueCalculator }/>
          <div id="spotType">{ currentSpot.spotType.type }</div>
          <div id="valuePerHour">{ currentSpot.spotType.value } USD per Hour</div>
          <div id="estimatedValue">Estimated Value: { estimatedValue } USD</div>
        </label>

        <button id="newParkingRequestButton" onClick={ () => console.log("clickou") }>Confirm new parking request</button>
      </form>
    </div>
  );

}

export default NewParkingComponent;
