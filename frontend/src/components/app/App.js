import React from "react";
import SearchAuthor from "./searchAuthor";
import {Component} from "react";
import Header from "./Header";
import {
  Routes,
  Navigate,
  Route, Router
} from 'react-router-dom';
import Service from "../../repository/Repository";

class App extends Component{
  render() {
    return (
        <div>
          <Header/>
          <div className={"container"}>
            <Routes>
              <Route path="/searchauthor" element={<SearchAuthor onsearch={this.searchAuthor} />} />
            </Routes>
          </div>
        </div>
    )
  }
  searchAuthor = (name) => {
    Service.searchAuthor(name)
        .then(() => {
          this.loadAuthor();
        })
  }
}

export default App;

