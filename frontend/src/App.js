import React, { useState } from "react"

function App() {
  const [inputValue, setInputValue] = useState('');
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState(null);

   const checkTags = () => {
    setLoading(true)
    fetch("http://localhost:8080/api/youtube/analyze", {
      method: "POST",
      headers: {
      "Content-Type": "application/json"
      },
      body: JSON.stringify({ tags: tags })
    }).then((response) =>{
      return response.json();
    }).then((data) => {
  setResults(data);
  setLoading(false);
  }).catch((error) => {
    console.error('Error:', error);
    setLoading(false);
  });; 
  };
  const handleChange = (event) => {
    setInputValue(event.target.value);
  };
  const handleSubmit = (event) => {
    setTags([...tags, inputValue]);
    setInputValue('');  
  }
  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      <input name="myInput" value = {inputValue} onChange={handleChange}/>
      <p>Current input: {inputValue}</p>
      <p>Tags: {tags.join(', ')}</p>
      <button onClick={handleSubmit}>Submit</button>
      <button onClick={checkTags}>checkTags</button>

      {loading && <p>Checking tags...</p>}
        {results && (
          <div>
            <h3>Results:</h3>
              {results.map((result, index) => (
                <div key={index}>
                <p>Tag: {result.tagName}</p>
                <p>Score: {result.tagScore}</p>
                <p>Total Results: {result.totalResults}</p>
                </div>
              ))}
          </div>
    )}
    </div>
    
  );
}

export default App;