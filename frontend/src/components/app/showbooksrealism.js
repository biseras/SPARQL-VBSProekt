import React from "react";

const Showrelbooks = (props) => {
    console.log("tuj", props)
    const { label, abstract, authors, birth, death } = props.show;
    return (
        <div style={{ backgroundColor: "#f8f8f8", padding: "20px" }}>
            <h1>{label}</h1>
            <p style={{ backgroundColor: "#ddd", padding: "10px" }}>{abstract}</p>
            <h3>Famous authors</h3>
            {authors && (
                <ul>
                    {authors.map((author, index) => (
                        <li key={index}>
                            {author}
                            {birth && birth[index] && ` (${birth[index].split("T")[0]} - `}
                            {death && death[index] && `${death[index].split("T")[0]})`}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Showrelbooks;