import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import CoreContext from "../contextApi/CoreContext";
import loadingLogo from "../static/resources/1488.gif"

function HistoryComponent() {
  const { histories } = useContext(CoreContext);
  const navigate = useNavigate();

  return (
    <div id="historiesContainer">
      { !histories && <img src={ loadingLogo } alt="loading"/> }
      { histories && histories.status !== 400 && <h4>{ histories.content.message }</h4>}
      { histories && <button onClick={ () => navigate("/home") }>Back to Home Page</button>}
      { 
        histories && histories.status === 200 &&
          histories.content.map((history) => (
            <div id="historyCard" style={{ background: history.finished ? "#40F99B" : "#E85D75"}}>
              <h2>history Id: { history.id }</h2>
              <h4>Entry date: { history.entryDate.replace("T", " ").split(".")[0] }</h4>
              <h4>Departure date: { history.departureDate.replace("T", " ").split(".")[0] }</h4>
              <h4>spot id: { history.spot.id }</h4>
              <h4>spot type: { history.spot.spotType.type }</h4>

              { history.spot.available === true && histories.content.every((history) => history.finished) && 
                <button onClick={ () => navigate(`/parking/${history.spot.id}`)}>rent this slot again</button>
              }
              { history.finished ? <h4>Finished</h4> : <button>pay the Parking rent</button>}
            </div> 
        ))
      }
    </div>
  );
}

export default HistoryComponent;
