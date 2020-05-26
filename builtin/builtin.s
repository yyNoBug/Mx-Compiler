	.file	"builtin.c"
	.option nopic
	.text
	.align	2
	.globl	__mallocObject__
	.type	__mallocObject__, @function
__mallocObject__:
	tail	malloc
	.size	__mallocObject__, .-__mallocObject__
	.align	2
	.globl	__mallocArray__
	.type	__mallocArray__, @function
__mallocArray__:
	addi	sp,sp,-16
	sw	s0,8(sp)
	mv	s0,a0
	addi	a0,a0,1
	slli	a0,a0,2
	sw	ra,12(sp)
	call	malloc
	sw	s0,0(a0)
	lw	ra,12(sp)
	lw	s0,8(sp)
	addi	a0,a0,4
	addi	sp,sp,16
	jr	ra
	.size	__mallocArray__, .-__mallocArray__
	.align	2
	.globl	__mallocString__
	.type	__mallocString__, @function
__mallocString__:
	addi	sp,sp,-16
	sw	s0,8(sp)
	mv	s0,a0
	addi	a0,a0,5
	sw	ra,12(sp)
	call	malloc
	sw	s0,0(a0)
	add	a5,a0,s0
	sb	zero,4(a5)
	lw	ra,12(sp)
	lw	s0,8(sp)
	addi	sp,sp,16
	jr	ra
	.size	__mallocString__, .-__mallocString__
	.section	.rodata.str1.4,"aMS",@progbits,1
	.align	2
.LC0:
	.string	"%d"
	.text
	.align	2
	.globl	__getInt__
	.type	__getInt__, @function
__getInt__:
	addi	sp,sp,-32
	lui	a0,%hi(.LC0)
	addi	a1,sp,12
	addi	a0,a0,%lo(.LC0)
	sw	ra,28(sp)
	call	__isoc99_scanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	__getInt__, .-__getInt__
	.section	.rodata.str1.4
	.align	2
.LC1:
	.string	"%s"
	.text
	.align	2
	.globl	__getString__
	.type	__getString__, @function
__getString__:
	addi	sp,sp,-16
	sw	s2,0(sp)
	lui	a0,%hi(.LC1)
	lui	s2,%hi(input)
	addi	a1,s2,%lo(input)
	addi	a0,a0,%lo(.LC1)
	sw	ra,12(sp)
	sw	s0,8(sp)
	sw	s1,4(sp)
	call	__isoc99_scanf
	addi	a0,s2,%lo(input)
	call	strlen
	mv	s1,a0
	addi	a0,a0,5
	call	malloc
	sw	s1,0(a0)
	add	a5,a0,s1
	mv	s0,a0
	addi	a2,s1,1
	addi	a1,s2,%lo(input)
	addi	a0,a0,4
	sb	zero,4(a5)
	call	memcpy
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	lw	s1,4(sp)
	lw	s2,0(sp)
	addi	sp,sp,16
	jr	ra
	.size	__getString__, .-__getString__
	.align	2
	.globl	__print__
	.type	__print__, @function
__print__:
	addi	a1,a0,4
	lui	a0,%hi(.LC1)
	addi	a0,a0,%lo(.LC1)
	tail	printf
	.size	__print__, .-__print__
	.align	2
	.globl	__println__
	.type	__println__, @function
__println__:
	addi	a0,a0,4
	tail	puts
	.size	__println__, .-__println__
	.align	2
	.globl	__printInt__
	.type	__printInt__, @function
__printInt__:
	mv	a1,a0
	lui	a0,%hi(.LC0)
	addi	a0,a0,%lo(.LC0)
	tail	printf
	.size	__printInt__, .-__printInt__
	.section	.rodata.str1.4
	.align	2
.LC2:
	.string	"%d\n"
	.text
	.align	2
	.globl	__printlnInt__
	.type	__printlnInt__, @function
__printlnInt__:
	mv	a1,a0
	lui	a0,%hi(.LC2)
	addi	a0,a0,%lo(.LC2)
	tail	printf
	.size	__printlnInt__, .-__printlnInt__
	.align	2
	.globl	__toString__
	.type	__toString__, @function
__toString__:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	sw	s1,20(sp)
	sw	s2,16(sp)
	sw	s3,12(sp)
	blt	a0,zero,.L27
	bne	a0,zero,.L23
	li	a5,48
	lui	s0,%hi(dStr)
	li	a0,6
	sb	a5,%lo(dStr)(s0)
	call	malloc
	lbu	a5,%lo(dStr)(s0)
	li	a4,1
	sw	a4,0(a0)
	sb	zero,5(a0)
	sb	a5,4(a0)
.L15:
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s1,20(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	addi	sp,sp,32
	jr	ra
.L27:
	neg	a0,a0
	li	s3,1
.L17:
	lui	s0,%hi(dStr)
	addi	s1,s0,%lo(dStr)
	li	a4,10
	addi	s0,s0,%lo(dStr)
.L19:
	rem	a5,a0,a4
	addi	s0,s0,1
	div	a0,a0,a4
	addi	a5,a5,48
	sb	a5,-1(s0)
	bne	a0,zero,.L19
	sub	s2,s0,s1
	add	s2,s2,s3
	addi	a0,s2,5
	call	malloc
	sw	s2,0(a0)
	add	s2,a0,s2
	sb	zero,4(s2)
	addi	a5,a0,4
	beq	s3,zero,.L21
	li	a4,45
	addi	a5,a0,5
	sb	a4,4(a0)
.L21:
	beq	s0,s1,.L15
.L22:
	lbu	a4,-1(s0)
	addi	s0,s0,-1
	addi	a5,a5,1
	sb	a4,-1(a5)
	bne	s0,s1,.L22
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s1,20(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	addi	sp,sp,32
	jr	ra
.L23:
	li	s3,0
	j	.L17
	.size	__toString__, .-__toString__
	.align	2
	.globl	__stringLength__
	.type	__stringLength__, @function
__stringLength__:
	lw	a0,0(a0)
	ret
	.size	__stringLength__, .-__stringLength__
	.align	2
	.globl	__stringOrder__
	.type	__stringOrder__, @function
__stringOrder__:
	add	a0,a0,a1
	lbu	a0,4(a0)
	ret
	.size	__stringOrder__, .-__stringOrder__
	.align	2
	.globl	__stringParseInt__
	.type	__stringParseInt__, @function
__stringParseInt__:
	addi	sp,sp,-32
	lui	a1,%hi(.LC0)
	addi	a2,sp,12
	addi	a1,a1,%lo(.LC0)
	addi	a0,a0,4
	sw	ra,28(sp)
	call	__isoc99_sscanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringParseInt__, .-__stringParseInt__
	.align	2
	.globl	__stringSubstring__
	.type	__stringSubstring__, @function
__stringSubstring__:
	addi	sp,sp,-32
	sw	s3,12(sp)
	sub	s3,a2,a1
	sw	s4,8(sp)
	mv	s4,a0
	addi	a0,s3,5
	sw	s0,24(sp)
	sw	s1,20(sp)
	sw	s2,16(sp)
	sw	ra,28(sp)
	mv	s0,a1
	mv	s2,a2
	call	malloc
	sw	s3,0(a0)
	add	a5,a0,s3
	sb	zero,4(a5)
	mv	s1,a0
	ble	s2,s0,.L32
	addi	a1,s0,4
	mv	a2,s3
	add	a1,s4,a1
	addi	a0,a0,4
	call	memcpy
.L32:
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	lw	s4,8(sp)
	mv	a0,s1
	lw	s1,20(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringSubstring__, .-__stringSubstring__
	.align	2
	.globl	__stringConcatenate__
	.type	__stringConcatenate__, @function
__stringConcatenate__:
	addi	sp,sp,-32
	sw	s4,8(sp)
	sw	s5,4(sp)
	lw	s4,0(a0)
	lw	s5,0(a1)
	sw	s1,20(sp)
	sw	s3,12(sp)
	add	s1,s4,s5
	mv	s3,a0
	addi	a0,s1,5
	sw	s0,24(sp)
	sw	s2,16(sp)
	sw	ra,28(sp)
	mv	s2,a1
	call	malloc
	sw	s1,0(a0)
	add	s1,a0,s1
	sb	zero,4(s1)
	mv	s0,a0
	addi	a5,a0,4
	ble	s4,zero,.L36
	mv	a0,a5
	mv	a2,s4
	addi	a1,s3,4
	call	memcpy
	add	a5,a0,s4
.L36:
	ble	s5,zero,.L35
	mv	a2,s5
	addi	a1,s2,4
	mv	a0,a5
	call	memcpy
.L35:
	lw	ra,28(sp)
	mv	a0,s0
	lw	s0,24(sp)
	lw	s1,20(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	lw	s4,8(sp)
	lw	s5,4(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringConcatenate__, .-__stringConcatenate__
	.align	2
	.globl	__stringEqual__
	.type	__stringEqual__, @function
__stringEqual__:
	lbu	a5,4(a0)
	lbu	a4,4(a1)
	addi	a0,a0,4
	addi	a1,a1,4
	beq	a4,a5,.L41
	j	.L42
.L46:
	beq	a4,zero,.L44
	lbu	a5,0(a0)
	lbu	a4,0(a1)
	bne	a5,a4,.L42
.L41:
	addi	a0,a0,1
	addi	a1,a1,1
	bne	a5,zero,.L46
.L44:
	li	a0,1
	ret
.L42:
	li	a0,0
	ret
	.size	__stringEqual__, .-__stringEqual__
	.align	2
	.globl	__stringNeq__
	.type	__stringNeq__, @function
__stringNeq__:
	mv	a5,a0
	lbu	a4,4(a1)
	lbu	a0,4(a0)
	addi	a5,a5,4
	addi	a1,a1,4
	beq	a4,a0,.L49
	j	.L50
.L56:
	beq	a4,zero,.L51
	lbu	a0,0(a5)
	lbu	a4,0(a1)
	bne	a0,a4,.L50
.L49:
	addi	a5,a5,1
	addi	a1,a1,1
	bne	a0,zero,.L56
	ret
.L50:
	li	a0,1
	ret
.L51:
	li	a0,0
	ret
	.size	__stringNeq__, .-__stringNeq__
	.align	2
	.globl	__stringLess__
	.type	__stringLess__, @function
__stringLess__:
	mv	a4,a0
	lbu	a5,4(a1)
	lbu	a0,4(a0)
	addi	a4,a4,4
	addi	a1,a1,4
	bleu	a5,a0,.L59
	j	.L60
.L67:
	beq	a0,zero,.L58
	beq	a5,zero,.L62
	lbu	a0,0(a4)
	lbu	a5,0(a1)
	bltu	a0,a5,.L60
.L59:
	addi	a4,a4,1
	addi	a1,a1,1
	bgeu	a5,a0,.L67
.L62:
	li	a0,0
.L58:
	ret
.L60:
	li	a0,1
	ret
	.size	__stringLess__, .-__stringLess__
	.align	2
	.globl	__stringLeq__
	.type	__stringLeq__, @function
__stringLeq__:
	lbu	a5,4(a0)
	lbu	a4,4(a1)
	addi	a0,a0,4
	addi	a1,a1,4
	bgeu	a4,a5,.L70
	j	.L71
.L76:
	beq	a5,zero,.L74
	beq	a4,zero,.L74
	lbu	a5,0(a0)
	lbu	a4,0(a1)
	bgtu	a5,a4,.L71
.L70:
	addi	a0,a0,1
	addi	a1,a1,1
	bleu	a4,a5,.L76
.L74:
	li	a0,1
	ret
.L71:
	li	a0,0
	ret
	.size	__stringLeq__, .-__stringLeq__
	.align	2
	.globl	__stringGreater__
	.type	__stringGreater__, @function
__stringGreater__:
	mv	a4,a0
	lbu	a5,4(a1)
	lbu	a0,4(a0)
	addi	a4,a4,4
	addi	a1,a1,4
	bgeu	a5,a0,.L79
	j	.L80
.L87:
	beq	a0,zero,.L78
	beq	a5,zero,.L82
	lbu	a0,0(a4)
	lbu	a5,0(a1)
	bgtu	a0,a5,.L80
.L79:
	addi	a4,a4,1
	addi	a1,a1,1
	bleu	a5,a0,.L87
.L82:
	li	a0,0
.L78:
	ret
.L80:
	li	a0,1
	ret
	.size	__stringGreater__, .-__stringGreater__
	.align	2
	.globl	__stringGeq__
	.type	__stringGeq__, @function
__stringGeq__:
	lbu	a5,4(a0)
	lbu	a4,4(a1)
	addi	a0,a0,4
	addi	a1,a1,4
	bleu	a4,a5,.L90
	j	.L91
.L96:
	beq	a5,zero,.L94
	beq	a4,zero,.L94
	lbu	a5,0(a0)
	lbu	a4,0(a1)
	bltu	a5,a4,.L91
.L90:
	addi	a0,a0,1
	addi	a1,a1,1
	bgeu	a4,a5,.L96
.L94:
	li	a0,1
	ret
.L91:
	li	a0,0
	ret
	.size	__stringGeq__, .-__stringGeq__
	.align	2
	.globl	__arraySize__
	.type	__arraySize__, @function
__arraySize__:
	lw	a0,-4(a0)
	ret
	.size	__arraySize__, .-__arraySize__
	.comm	dStr,13,4
	.comm	input,257,4
	.ident	"GCC: (GNU) 9.2.0"
	.section	.note.GNU-stack,"",@progbits
