import React, { useState } from "react"
import { Introduction } from "./components/intro";

function App() {
  const [inputValue, setInputValue] = useState('');
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState(null);
  const [history, setHistory] = useState([]);

  const checkTags = () => {
    if(tags.length > 0)
    {
    setLoading(true)
    fetch("https://youtube-tag-checker-production.up.railway.app/api/youtube/analyze", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ tags: tags })
    }).then((response) => {
      return response.json();
    }).then((data) => {
      setResults(data);
      setTags([]);
      setLoading(false);
    }).catch((error) => {
      console.error('Error:', error);
      setLoading(false);
    });
    }
  };
  
  const checkHistory = () => {
    setLoading(true)
    fetch("https://youtube-tag-checker-production.up.railway.app/api/youtube/history", {
      method: "GET",
    }).then((response) => {
      return response.json();
    }).then((data) => {
      setHistory(data);
      setLoading(false);
    }).catch((error) => {
      console.error('Error:', error);
      setLoading(false);
    });
  };

  const handleChange = (event) => {
    setInputValue(event.target.value);
  };
  const handleSubmit = (event) => {
    if(inputValue.length > 0)
    {
    setTags([...tags, inputValue]);
    setInputValue([]);
    }
  }
  const getCompetitionLevel = (score) =>{
    if (score < 30) {return "low";}
    if (score < 60) {return "medium";}
    return "high";
  }
  return (

    <div className= "container">
      <h1 className="color-primary"><span className="color-youtube">Youtube</span> Tag Checker</h1>
      <div className="input-section">
        <label htmlFor="tagInput" className="input-label">Input Tags</label>
        <input 
        id="tagInput"
        name="myInput" 
        className="tag-input"
        value={inputValue} 
        onChange={handleChange}
        placeholder="e.g Minecraft tutorial" />
        <p className="input-helper">Current input: {inputValue}</p>
        <p className="input-helper">Tags: {tags.join(', ')}</p>
      </div>
      <div className="button-group">
        <button onClick={handleSubmit} className="btn btn-primary">Add Tag(s)</button>
        <button onClick={checkTags} className="btn btn-primary">Analyze Tags</button>
        <button onClick={checkHistory} className="btn btn-primary">View History</button>
      </div>
      

      {loading && <p>Checking tags...</p>}
      {results && (
        <div className="results-section">
          <h2>Results:</h2>
        <div className="results-grid">
      {results.map((result, index) => (
        <div key = {index} className={`result-card ${getCompetitionLevel(result.tagScore)}-competition`}>
          <h3>{result.tagName}</h3>
          <p>Score: <span className="score">{Number(result.tagScore).toFixed(1)}</span></p>
          <p>Total Results: {result.totalResults.toLocaleString()}</p>
        </div>
      ))}
      </div>
  </div>
  )}

      <div>
        {history && (
          <div className="history-section">
            <h2> Previous Results:
              {history.map((historical, index) => (
                <div key={index}>
                <p>Tag: {historical.tagName}</p>
                <p>Score: {historical.tagScore}</p>
                <p>Total Results: {historical.totalResults}</p>
                <p>Created On: {historical.createdOn}</p>
            </div>
              ))}
            </h2>
            </div>
        )}
      </div>
    </div>

  );
}

export default App;