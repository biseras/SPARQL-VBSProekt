import React from "react";
import SearchAuthor from "./searchAuthor";
import {Component} from "react";
import Header from "./Header";
import {
  Routes,
  Navigate,
  Route, Router
} from 'react-router-dom';
import SearchResult from './searchResult';
import Service from "../../repository/Repository";

class App extends Component{
    constructor(props) {
        super(props);
        this.state={
            author:[]
        }
    }
  render() {
    return (
        <div>
          <Header/>
          <div className={"container"}>
            <Routes>
                <Route path="/searchauthor" element={<SearchAuthor onsearch={this.searchAuthor} />} />
                <Route path="/result" element={<SearchResult/>} />
            </Routes>
          </div>
        </div>
    )
  }
  searchAuthor = (name) => {
    Service.searchAuthor(name)
        .then((data)=>{

          this.setState({
              author:data.data
          })
      });
  }
}

export default App;

