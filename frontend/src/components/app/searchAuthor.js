import React, { useState } from 'react';
import Service from "../../repository/Repository";
import { useNavigate } from 'react-router-dom';

const SearchAuthor = (props) => {
    const [responestate, updatedata] = React.useState()
    const history = useNavigate();
    const onFormSubmit =async (e) => {
        e.preventDefault();
        const name = e.target.name.value;
        let response=await Service.searchAuthor(name)
        console.log(response)
        updatedata(response.data)
        history('/result', {state:response});
    }
    return (
        <div style={{
            backgroundColor: 'lightpink',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh',
        }}>
            <div style={{
                backgroundColor: 'white',
                border: '5px solid black',
                padding: '20px',
                borderRadius: '10px',
                fontSize: '24px',
                textAlign: 'center',
            }}>
                <p style={{ color: 'black', font:'20' }}><i>Enter the author name to see more data from dbpedia about it</i></p>
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control"
                            id="name"
                            name="name"
                            required
                            placeholder="Enter author name"
                            style={{ fontSize: '18px', marginBottom: '10px', width: '300px' }}
                        />
                    </div>
                    <br />
                    <button type="submit" className="btn btn-primary" style={{ fontSize: '18px' }}>Submit</button>
                </form>
            </div>
        </div>
    )
}
export default SearchAuthor;