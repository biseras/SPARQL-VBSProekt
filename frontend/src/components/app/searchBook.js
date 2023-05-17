import React, { useState } from 'react';
import Service from "../../repository/Repository";
import { useNavigate } from 'react-router-dom';

const SearchBook = (props) => {
    const [responestate, updatedata] = React.useState()
    const history = useNavigate();
    const onFormSubmit =async (e) => {
        e.preventDefault();
        const name = e.target.name.value;
        let response=await Service.searchBook(name)
        console.log(response)
        updatedata(response.data)
        history('/resultbook', {state:response});
    }
    return (
        <div style={{ backgroundColor: "#f8f9fa", color: "#212529", margin: "50px", padding: "20px"}}>
            <p style={{ fontSize: "26px", marginBottom: "20px" }}>Famous books have played a significant role in shaping the literary canon and have become an integral part of our cultural heritage. These timeless works have stood the test of time due to their literary excellence and profound impact on society. From the remarkable storytelling techniques to the masterful use of language, famous books captivate readers and transport them to different worlds, stirring their imagination and emotions. These works often address universal themes such as love, loss, identity, and the human condition, resonating with readers from diverse backgrounds and generations. They offer insights into the complexities of the human experience, prompting introspection and fostering empathy. Moreover, famous books serve as a window into different historical periods, providing a deeper understanding of the past and shaping our collective memory. </p>
            <br/>
            <h1 style={{ fontSize: "36px", marginBottom: "20px" }}>Here you can find informations about famous books</h1>
            <div style={{ display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column" }}>
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <h3 style={{ fontSize: '18px', color: 'black' }}>Enter book name to see more data from dbpedia about it</h3>
                        <input type="text"
                               className="form-control"
                               id="name"
                               name="name"
                               required
                               placeholder="Enter book name"
                               style={{ fontSize: '18px', marginTop: '10px' }}
                        />
                    </div>
                    <br/>
                    <button id="submit" type="submit" className="btn btn-primary" style={{ backgroundColor: '#E6E6E6', color: 'gray', border: 'none', borderRadius: '5px', fontSize: '20px' }}>Submit</button>
                </form>
            </div>
        </div>

    )
}
export default SearchBook;