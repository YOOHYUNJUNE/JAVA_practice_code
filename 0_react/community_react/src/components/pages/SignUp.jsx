import styled from '@emotion/styled';
import useInputs from "../../hooks/useInputs";
import axios from "axios";
import { useState } from "react";
import { Box, Button, FormControl, FormGroup, IconButton, InputAdornment, InputLabel, OutlinedInput, TextField, Typography } from '@mui/material';
import { IoEye } from "react-icons/io5";
import { IoMdEyeOff } from "react-icons/io";
import { userApi } from '../../api/services/users';

const SignUp = () => {
    const {form, handleChange, handleReset} = useInputs({
        email: "",
        nickname: "",
        password: "",
        password_chk: ""
    });
    const { email, nickname, password, password_chk } = form;

    const [ isDuplicate, setIsDuplicate ] = useState(false);
    const [errors, setErrors] = useState({});
    const [showPassword, setShowPassword] = useState(false);

    const handleTogglePassword = () => setShowPassword(!showPassword);
    const handleMouseDownPassword = (e) => e.preventDefault();
    const handleDuplicate = async() => {
        // email 중복 체크
        if (email.trim()) {
            try {
                const res = await userApi.getUserByEmail(email);
                if (!res.data.length) {
                    setErrors({});
                    setIsDuplicate(true);
                    alert("사용 가능한 이메일입니다.");
                } else {
                    setErrors({email: "이미 존재하는 이메일입니다."});
                    setIsDuplicate(false);
                }
            } catch (error) {
                console.error(error);
            }
        }
    }

    const resetDuplicate = (e) => {
        setIsDuplicate(false);
        handleChange(e);
    }

    const validate = () => {
        let isValid = true;
        const newErrors = {};
        if (!isDuplicate) {
            newErrors.email = `중복 체크 해주세요`;
        }
        // password_chk 일치 여부 체크
        if (form.password !== form.password_chk) {
            isValid = false;
            newErrors.password_chk = `비밀번호 불일치입니다`;
        }
        for(const [key, value] of Object.entries(form)) {
            // 빈값 여부 체크
            if(!value.trim()) {
                isValid = false;
                newErrors[key] = `해당 값을 입력해주세요`;
            }
        }
        console.log(newErrors);
        setErrors(newErrors);
        return isValid;
    }

    const handleSignUp = async(e) => {
        e.preventDefault();
        if (isDuplicate && validate()) {
            const user = { email, nickname, password };
            try {
                const res = await userApi.postUser(user);
                if (res.status === 201) {
                    alert('회원가입 완료');
                } else {
                    throw new Error("회원가입 실패");
                }
            } catch (error) {
                console.error(error);
            } finally {
                handleReset();
            }
        }
    }
    return (
        // 회원가입할 때, { email, nickname, password, password_chk}
        <>
            <Typography variant='h4'>회원가입</Typography>
            <Box
                component={"form"}
                my={4}
                p={2}
                borderRadius={4}
                boxShadow={'0 0 4px grey'}
                width={"fit-content"}
                sx={{
                    margin: '0 auto',
                    '& > :not(style)' : { m : 1, width: '100%'}
                }}
                noValidate
                autoComplete='off'
            >
                <FormGroup sx={{flexDirection: 'row', justifyContent: 'space-between'}}>
                    <TextField
                        label="이메일"
                        variant='outlined'
                        autoFocus
                        required
                        type='email' id='email' name='email' value={email} onChange={resetDuplicate}
                        error={errors.email ? true : false}
                        helperText={errors.email}
                    />
                    <Button onClick={handleDuplicate} variant='contained'>중복 확인</Button>
                </FormGroup>
                <TextField
                    label="닉네임"
                    variant='outlined'
                    sx={{display: 'block'}}
                    required
                    id='nickname' name='nickname' value={nickname} onChange={handleChange} 
                    error={errors.nickname ? true : false}
                    helperText={errors.nickname}
                />
                <FormControl sx={{m:1, width: '100%', display: 'block'}} variant='outlined'>
                    <InputLabel sx={errors.password && {color: '#d32f2f'}} htmlFor="password">비밀번호 *</InputLabel>
                    <OutlinedInput
                        type={showPassword ? 'text' : 'password'} id='password' name='password' value={password} onChange={handleChange} 
                        required
                        autoComplete='new-password'
                        label='비밀번호 *'
                        endAdornment={
                            <InputAdornment position='end'>
                                <IconButton
                                    aria-label='toggle password visibility'
                                    onClick={handleTogglePassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge="end"
                                >
                                    {showPassword ? <IoEye/> : <IoMdEyeOff/>}
                                </IconButton>
                            </InputAdornment>
                        }
                        error={errors.password ? true : false}
                    />

                </FormControl>
                <FormControl sx={{m:1, width: '100%', display: 'block'}} variant='outlined'>
                    <InputLabel sx={errors.password_chk && {color: '#d32f2f'}} htmlFor="password_chk">비밀번호 확인 *</InputLabel>
                    <OutlinedInput
                        type={showPassword ? 'text' : 'password'} id='password_chk' name='password_chk' value={password_chk} onChange={handleChange}
                        required
                        autoComplete='new-password'
                        label='비밀번호 확인 *'
                        endAdornment={
                            <InputAdornment position='end'>
                                <IconButton
                                    aria-label='toggle password visibility'
                                    onClick={handleTogglePassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge="end"
                                >
                                    {showPassword ? <IoEye/> : <IoMdEyeOff/>}
                                </IconButton>
                            </InputAdornment>
                        }
                        error={errors.password_chk ? true : false}
                    />
                    <ErrorMsg>{errors.password || errors.password_chk}</ErrorMsg>
                </FormControl>
                <Button onClick={handleSignUp}>가입</Button>
            </Box>
        </>
    );
}

const ErrorMsg = styled.p`
    color: #d32f2f;
    font-family: "Roboto","Helvetica","Arial",sans-serif;
    font-weight: 400;
    font-size: 0.75rem;
    line-height: 1.66;
    letter-spacing: 0.03333em;
    text-align: left;
    margin: 3px 14px 0 14px;
`

export default SignUp;