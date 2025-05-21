import React, {forwardRef, useEffect, useImperativeHandle, useState} from 'react';
import axios from 'axios';
import {
    Card,
    CardContent,
    Grid,
    TextField,
    Typography,
    Select,
    MenuItem,
    FormControl,
    InputLabel
} from '@mui/material';

const Information = forwardRef((props, ref) => {
    const [profile, setProfile] = useState({});
    const [buseoName, setBuseoName] = useState('');
    const [currentDate, setCurrentDate] = useState('');
    const [openStatus, setOpenStatus] = useState('공개');

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const res = await axios.get('http://localhost:8080/main/profile', {
                    withCredentials: true,
                });

                setProfile(res.data.user);
                setBuseoName(res.data.buseoName);
            } catch (error) {
                console.error('프로필 가져오기 실패:', error);
            }
        };

        const today = new Date().toISOString().split('T')[0];
        setCurrentDate(today);
        fetchProfile();
    }, []);

    useImperativeHandle(ref, ()=> ({
        getData:()=>({
            userName:profile.userName||'',
            userBuseo:profile.userBuseo,
            documentDate:currentDate,
            documentTitle:"계출서",
            documentOpen:openStatus
        })
    }));


    return (
        <Card sx={{ borderRadius: 2, width: '100%' }}>
            <CardContent sx={{ p: 3 }}>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                    기본 정보
                </Typography>
                <Grid container spacing={2}>
                    {/* 문서번호 (비활성화) */}
                    <Grid item xs={12} sm={6} md={3}>
                        <TextField
                            fullWidth
                            label="문서번호"
                            value=""
                            disabled
                            sx={{
                                backgroundColor: '#f5f5f5',
                                '& .Mui-disabled': {
                                    color: '#999',
                                },
                            }}
                        />
                    </Grid>
                    {/* 작성일자 (달력 선택 가능) */}
                    <Grid item xs={12} sm={6} md={3}>
                        <TextField
                            fullWidth
                            label="작성일자"
                            type="date"
                            value={currentDate}
                            onChange={(e) => setCurrentDate(e.target.value)}
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    {/* 부서 */}
                    <Grid item xs={12} sm={6} md={3}>
                        <TextField fullWidth label="부서" value={buseoName} />
                    </Grid>
                    {/* 작성자 */}
                    <Grid item xs={12} sm={6} md={3}>
                        <TextField fullWidth label="작성자" value={profile.userName || ''} />
                    </Grid>

                    {/* 제목 (위로 올리고 너비 늘리기) */}
                    <Grid item xs={12} md={3} width={300}>
                        <TextField
                            fullWidth
                            label="제목"
                            value="계출서"
                            sx={{
                                '& .MuiInputLabel-root': {
                                    color: '#009688',
                                },
                            }}
                        />
                    </Grid>
                    {/* 공개여부 (선택 가능하도록) */}
                    <Grid item xs={12} md={3}>
                        <FormControl fullWidth>
                            <InputLabel>공개여부</InputLabel>
                            <Select
                                value={openStatus}
                                label="공개여부"
                                onChange={(e) => setOpenStatus(e.target.value)}
                            >
                                <MenuItem value="공개">공개</MenuItem>
                                <MenuItem value="비공개">비공개</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
            </CardContent>
        </Card>
    );
});

export default Information;
