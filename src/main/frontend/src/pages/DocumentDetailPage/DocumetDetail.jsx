import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Box, Container, Typography, Grid } from '@mui/material';
import ReadonlyInformation from "./ReadonlyInformation";
import ReadonlyRegister from "./ReadonlyRegister";
import ReadonlyUseyear from "./ReadonlyUseyear";


function DocumentDetail() {
    const { id } = useParams();
    const [document, setDocument] = useState(null);
    const [vacations, setVacations] = useState([]);
    const [leave, setLeave] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/document/${id}`, { withCredentials: true })
            .then(res => {
                setDocument(res.data.document);
                setVacations(res.data.vacations || []);
                setLeave(res.data.leave);
            })
            .catch(err => console.error('문서 상세 가져오기 실패:', err));
    }, [id]);

    if (!document) {
        return <Typography>로딩 중...</Typography>;
    }

    // 정보 객체 변환 (ReadonlyInformation 컴포넌트와 호환되도록 변환)
    const infoData = {
        userName: document.user.userName,
        userBuseo: document.user.userBuseo,
        documentTitle: document.documentTitle,
        documentDate: document.docmentDate,
        documentOpen: document.documentOpen === 1 ? '공개' : '비공개',
    };

    return (
        <Box sx={{ backgroundColor: '#f4f6f8', minHeight: '100vh', py: 4 }}>
            <Container maxWidth="md">
                <Typography variant="h5" fontWeight="bold" gutterBottom>
                    문서 상세 보기
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}><ReadonlyInformation data={infoData} /></Grid>
                    <Grid item xs={12}><ReadonlyRegister data={vacations} /></Grid>
                    <Grid item xs={12}><ReadonlyUseyear data={leave} /></Grid>
                </Grid>
            </Container>
        </Box>
    );
}

export default DocumentDetail;
