import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import CoreContext from "../contextApi/CoreContext";
import loadingLogo from "../static/resources/1476.gif"
import "../static/styles/historyPageStyle.css"

function HistoryComponent() {
  const { histories } = useContext(CoreContext);
  const navigate = useNavigate();

  return (
    <div id="historiesContainer">
      { !histories && <img id="loadingImage" src={ loadingLogo } alt="loading"/> }
      {
        histories && <div id="menuBar">
          <button onClick={ () => navigate("/home") }>Back to Home Page</button>
        </div>
      }
      { 
        histories && histories.status === 200 &&
          histories.content.map((history) => (
            <div id="historyCard" style={{ background: history.finished ? "#fffaff" : "#d8315b", color: history.finished ? "#d8315b" : "#fffaff" }}>
              <h2>history Id: { history.id }</h2>
              <h4>Entry date: { history.entryDate.replace("T", " ").split(".")[0] }</h4>
              <h4>Departure date: { history.departureDate.replace("T", " ").split(".")[0] }</h4>
              <h4>spot id: { history.spot.id }</h4>
              <h4>spot type: { history.spot.spotType.type }</h4>

              { history.spot.available === true && histories.content.every((history) => history.finished) && 
                <button onClick={ () => navigate(`/parking/${history.spot.id}`)}>rent this slot again</button>
              }
              { history.finished ? <h4 className="finishedTitle">FINISHED</h4> : <button onClick={ () => navigate(`/payment`)}>pay the Parking rent</button>}
            </div> 
        ))
      }
      { histories && histories.status !== 200 && <h4 id="errorMessage">{ histories.content.message }</h4>}
    </div>
  );
}

export default HistoryComponent;
