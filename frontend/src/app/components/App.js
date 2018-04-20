import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import JobsTable from './JobsTable'
import JobsForm from './JobsForm'
import { BrowserRouter as Router, Link, Route } from 'react-router-dom';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.handleToggleDrawer = this.handleToggleDrawer.bind(this);
    this.handleCloseDrawer = this.handleCloseDrawer.bind(this);
    this.state = {open: false};
  }

  handleToggleDrawer() {
    this.setState({open: !this.state.open});
  }

  handleCloseDrawer() {
    this.setState({open: false})
  }

  render() {
    return (
      <Router>
        <MuiThemeProvider>
          <div>
            <AppBar
              title="Slack Scheduler"
              iconClassNameRight="muidocs-icon-navigation-expand-more"
              onLeftIconButtonClick={this.handleToggleDrawer}
            />
            <Drawer docked={false} open={this.state.open} onRequestChange={this.handleCloseDrawer}>
              <Link to={'/create'} style={{ textDecoration: 'none' }}>
                <MenuItem onClick={this.handleCloseDrawer}>
                Create job
                </MenuItem>
              </Link>


              <Link to={'/table'} style={{ textDecoration: 'none' }}>
                <MenuItem onClick={this.handleCloseDrawer}>
                Jobs
                </MenuItem>
              </Link>
            </Drawer>
            <Route exact={true} path='/' component={JobsTable}/>
            <Route path='/table' component={JobsTable}/>
            <Route path='/create' component={JobsForm}/>
          </div>
        </MuiThemeProvider>
      </Router>
    );
  }
}

export default App;