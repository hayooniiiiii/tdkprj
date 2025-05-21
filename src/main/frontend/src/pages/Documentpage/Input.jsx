// Input.jsx
import React, { useRef } from 'react';
import { Box, Container, Grid, Typography, Button } from '@mui/material';
import Information from "../Documentmodal/Information";
import Register from "../Documentmodal/Register";
import Useyear from "../Documentmodal/Useyear";
import axios from "axios";

export function Input() {
    const infoRef = useRef();
    const registerRef = useRef();
    const useyearRef = useRef();

    const handleSave = async () => {
        const informationData = infoRef.current?.getData();
        const registerData = registerRef.current?.getData();
        const useyearData = useyearRef.current?.getData();

        const payload = {
            information: informationData,
            vacationDto: registerData || [],
            useYear: useyearData || null
        };

        try {
            console.log(payload);
            await axios.post('http://localhost:8080/document/save', payload, {
                withCredentials: true
            });
            alert('저장 완료');
        } catch (error) {
            console.error('저장 실패:', error);
            alert('저장 실패');
        }
    };

    return (
        <Box sx={{ backgroundColor: '#f4f6f8', minHeight: '100vh', py: 4 }}>
            <Container maxWidth={false}>
                <Box sx={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
                    <Box sx={{ width: 1000 }}>
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
                            <Typography variant="h5" fontWeight="bold">계출서</Typography>
                            <Box>
                                <Button
                                    variant="outlined"
                                    onClick={handleSave}
                                    sx={{
                                        color: '#009688',
                                        borderColor: '#009688',
                                        '&:hover': {
                                            backgroundColor: '#009688',
                                            color: 'white',
                                            borderColor: '#00796b',
                                        },
                                        mr: 1,
                                    }}
                                >
                                    저장
                                </Button>
                                <Button
                                    variant="outlined"
                                    sx={{
                                        color: '#009688',
                                        borderColor: '#009688',
                                        '&:hover': {
                                            backgroundColor: '#009688',
                                            color: 'white',
                                            borderColor: '#00796b',
                                        },
                                    }}
                                >
                                    신규
                                </Button>
                            </Box>
                        </Box>
                        <Grid container spacing={3}>
                            <Grid item xs={12} width={1000}><Information ref={infoRef} /></Grid>
                            <Grid item xs={12} width={1000}><Register ref={registerRef} /></Grid>
                            <Grid item xs={12} width={1000}><Useyear ref={useyearRef} /></Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </Box>
    );
}

export default Input;
