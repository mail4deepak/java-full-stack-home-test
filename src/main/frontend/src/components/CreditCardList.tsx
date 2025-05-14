import React from 'react';
import {
  Card,
  CardContent,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Alert,
} from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

interface CreditCard {
  id: number;
  name: string;
  cardNumber: string;
  limit: number;
  balance: number;
}

const apiUrl = process.env.REACT_APP_API_URL;

const CreditCardList: React.FC = () => {
  const { data: cards, isLoading, error } = useQuery<CreditCard[]>({
    queryKey: ['cards'],
    queryFn: () => axios.get(`${apiUrl}/api/v1/cards`).then((res) => res.data),
  });

  if (isLoading) {
    return (
      <Card>
        <CardContent>
          <Typography>Loading credit cards...</Typography>
        </CardContent>
      </Card>
    );
  }

  if (error) {
    return (
      <Card>
        <CardContent>
          <Alert severity="error">Failed to load credit cards</Alert>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="h2" gutterBottom>
          Existing Cards
        </Typography>
        <TableContainer component={Paper}>
          <Table>
            <TableHead style={{backgroundColor:'#C0C2C9',}}>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Card Number</TableCell>
                <TableCell>Balance</TableCell>
                <TableCell>Limit</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {cards?.map((card) => (
                <TableRow key={card.id}>
                  <TableCell>{card.name}</TableCell>
                  <TableCell>{card.cardNumber}</TableCell>
                  <TableCell sx={{ 
                      color: card.balance < 0 ? 'error.main' : 'inherit',
                      fontWeight: card.balance < 0 ? 'bold' : 'normal'
                    }}>£{card.balance.toFixed(2)}</TableCell>
                  <TableCell>£{card.limit.toFixed(2)}</TableCell>
                </TableRow>
              ))}
              {(!cards || cards.length === 0) && (
                <TableRow>
                  <TableCell colSpan={4} align="center">
                    No credit cards found
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </CardContent>
    </Card>
  );
};

export default CreditCardList; 