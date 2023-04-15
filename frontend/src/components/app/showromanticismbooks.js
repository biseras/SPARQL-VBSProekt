import React from "react";

const Showrombooks = (props) => {
    console.log("tuj", props)
    return (
        <div style={{backgroundColor: '#f8f8f8', padding: '20px'}}>
            <h1>{props.show.label}</h1>
            <p style={{backgroundColor: '#ddd', padding: '10px'}}>{props.show.abstract}</p>
            <ul>
                {props.show.authors.map((author, index) => (
                    <li key={index} >{author}</li>
                ))}
            </ul>
        </div>
    );
}

export default Showrombooks;