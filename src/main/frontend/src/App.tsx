import React from 'react';
import { Container, CssBaseline, ThemeProvider, createTheme, Typography } from '@mui/material';
import CreditCardForm from './components/CreditCardForm';
import CreditCardList from './components/CreditCardList';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography variant="h4" component="h2" gutterBottom>
          Credit Card System
        </Typography>
        <CreditCardForm />
        <CreditCardList />
      </Container>
    </ThemeProvider>
  );
}

export default App; 