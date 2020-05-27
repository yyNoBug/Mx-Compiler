	.section	.data
global0:
	.zero	4
	.section	.rodata
string0:
	.word	1
	.string	"2"
string1:
	.word	1
	.string	" "
string2:
	.word	1
	.string	"1"
string3:
	.word	1
	.string	"3"
string4:
	.word	1
	.string	" "
string5:
	.word	1
	.string	" "
	.section	text
	.globl	main
init:
.init:
	addi	sp,sp,-16
	sw	ra,12(sp)
.init.entry:
	call	__getInt__
	mv	t5,a0
	sw	t5,8(sp)
	lw	t3,8(sp)
	sw	t3,global0,t5
	sw	t5,4(sp)
	li	t3,0
	sw	t3,0(sp)
	lw	t3,0(sp)
	mv	a0,t3
	j	.init.exit
.init.exit:
	lw	ra,12(sp)
	addi	sp,sp,16
	jr	ra
is_prime:
.is_prime:
	addi	sp,sp,-128
	sw	ra,124(sp)
.is_prime.entry:
	li	t3,120
	sw	t3,112(sp)
	lw	t4,112(sp)
	add	t5,sp,t4
	sw	t5,108(sp)
	lw	t4,108(sp)
	sw	a0,0(t4)
	lw	t4,108(sp)
	lw	t5,0(t4)
	sw	t5,104(sp)
	li	t3,1
	sw	t3,100(sp)
	lw	t3,104(sp)
	lw	t4,100(sp)
	sub	t5,t3,t4
	sw	t5,96(sp)
	lw	t3,96(sp)
	seqz	t5,t3
	sw	t5,92(sp)
	lw	t3,92(sp)
	beqz	t3,.if.0.end
	j	.if.0.then
.if.0.then:
	li	t3,0
	sw	t3,88(sp)
	lw	t3,88(sp)
	mv	a0,t3
	j	.is_prime.exit
	j	.if.0.end
.if.0.else:
	j	.if.0.end
.if.0.end:
	li	t3,116
	sw	t3,84(sp)
	lw	t4,84(sp)
	add	t5,sp,t4
	sw	t5,80(sp)
	li	t3,2
	sw	t3,76(sp)
	lw	t4,80(sp)
	lw	t3,76(sp)
	sw	t3,0(t4)
	j	.for.0.condition
.for.0.condition:
	lw	t4,80(sp)
	lw	t5,0(t4)
	sw	t5,72(sp)
	lw	t4,80(sp)
	lw	t5,0(t4)
	sw	t5,68(sp)
	lw	t3,72(sp)
	lw	t4,68(sp)
	mul	t5,t3,t4
	sw	t5,64(sp)
	lw	t4,108(sp)
	lw	t5,0(t4)
	sw	t5,60(sp)
	lw	t3,64(sp)
	lw	t4,60(sp)
	sub	t5,t3,t4
	sw	t5,56(sp)
	lw	t3,56(sp)
	sgtz	t5,t3
	sw	t5,52(sp)
	lw	t3,52(sp)
	xori	t5,t3,1
	sw	t5,48(sp)
	lw	t3,48(sp)
	beqz	t3,.for.0.end
	j	.for.0.body
.for.0.body:
	lw	t4,108(sp)
	lw	t5,0(t4)
	sw	t5,44(sp)
	lw	t4,80(sp)
	lw	t5,0(t4)
	sw	t5,40(sp)
	lw	t3,44(sp)
	lw	t4,40(sp)
	rem	t5,t3,t4
	sw	t5,36(sp)
	li	t3,0
	sw	t3,32(sp)
	lw	t3,36(sp)
	lw	t4,32(sp)
	sub	t5,t3,t4
	sw	t5,28(sp)
	lw	t3,28(sp)
	seqz	t5,t3
	sw	t5,24(sp)
	lw	t3,24(sp)
	beqz	t3,.if.1.end
	j	.if.1.then
.if.1.then:
	li	t3,0
	sw	t3,20(sp)
	lw	t3,20(sp)
	mv	a0,t3
	j	.is_prime.exit
	j	.if.1.end
.if.1.else:
	j	.if.1.end
.if.1.end:
	j	.for.0.step
.for.0.step:
	lw	t4,80(sp)
	lw	t5,0(t4)
	sw	t5,16(sp)
	li	t3,1
	sw	t3,12(sp)
	lw	t3,16(sp)
	lw	t4,12(sp)
	add	t5,t3,t4
	sw	t5,8(sp)
	lw	t4,80(sp)
	lw	t3,8(sp)
	sw	t3,0(t4)
	j	.for.0.condition
.for.0.end:
	li	t3,1
	sw	t3,4(sp)
	lw	t3,4(sp)
	mv	a0,t3
	j	.is_prime.exit
.is_prime.exit:
	lw	ra,124(sp)
	addi	sp,sp,128
	jr	ra
find:
.find:
	addi	sp,sp,-368
	sw	ra,364(sp)
.find.entry:
	li	t3,360
	sw	t3,344(sp)
	lw	t4,344(sp)
	add	t5,sp,t4
	sw	t5,340(sp)
	lw	t4,340(sp)
	sw	a0,0(t4)
	li	t3,356
	sw	t3,336(sp)
	lw	t4,336(sp)
	add	t5,sp,t4
	sw	t5,332(sp)
	lw	t4,332(sp)
	sw	a1,0(t4)
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,328(sp)
	lw	t3,328(sp)
	mv	a0,t3
	call	is_prime
	mv	t5,a0
	sw	t5,324(sp)
	lw	t3,324(sp)
	beqz	t3,.if.2.end
	j	.if.2.then
.if.2.then:
	lw	t4,332(sp)
	lw	t5,0(t4)
	sw	t5,320(sp)
	li	t3,0
	sw	t3,316(sp)
	lw	t3,320(sp)
	lw	t4,316(sp)
	sub	t5,t3,t4
	sw	t5,312(sp)
	lw	t3,312(sp)
	sltz	t5,t3
	sw	t5,308(sp)
	lw	t3,308(sp)
	xori	t5,t3,1
	sw	t5,304(sp)
	lw	t3,304(sp)
	beqz	t3,.if.3.else
	j	.if.3.then
.if.3.then:
	la	t5,string0
	sw	t5,300(sp)
	lw	t3,300(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,296(sp)
	lw	t4,332(sp)
	lw	t5,0(t4)
	sw	t5,292(sp)
	lw	t3,292(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,288(sp)
	lw	t3,288(sp)
	mv	a0,t3
	la	t5,string1
	sw	t5,284(sp)
	lw	t3,284(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,280(sp)
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,276(sp)
	lw	t3,276(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,272(sp)
	lw	t3,280(sp)
	mv	a0,t3
	lw	t3,272(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,268(sp)
	lw	t3,268(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,264(sp)
	j	.if.3.end
.if.3.else:
	la	t5,string2
	sw	t5,260(sp)
	lw	t3,260(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,256(sp)
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,252(sp)
	lw	t3,252(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,248(sp)
	lw	t3,248(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,244(sp)
	j	.if.3.end
.if.3.end:
	j	.find.exit
	j	.if.2.end
.if.2.else:
	j	.if.2.end
.if.2.end:
	li	t3,352
	sw	t3,240(sp)
	lw	t4,240(sp)
	add	t5,sp,t4
	sw	t5,236(sp)
	li	t3,5
	sw	t3,232(sp)
	lw	t4,236(sp)
	lw	t3,232(sp)
	sw	t3,0(t4)
	lw	t4,332(sp)
	lw	t5,0(t4)
	sw	t5,228(sp)
	li	t3,0
	sw	t3,224(sp)
	li	t3,1
	sw	t3,220(sp)
	lw	t3,224(sp)
	lw	t4,220(sp)
	sub	t5,t3,t4
	sw	t5,216(sp)
	lw	t3,228(sp)
	lw	t4,216(sp)
	sub	t5,t3,t4
	sw	t5,212(sp)
	lw	t3,212(sp)
	seqz	t5,t3
	sw	t5,208(sp)
	lw	t3,208(sp)
	beqz	t3,.if.4.else
	j	.if.4.then
.if.4.then:
	li	t3,348
	sw	t3,204(sp)
	lw	t4,204(sp)
	add	t5,sp,t4
	sw	t5,200(sp)
	li	t3,6
	sw	t3,196(sp)
	lw	t4,200(sp)
	lw	t3,196(sp)
	sw	t3,0(t4)
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,192(sp)
	li	t3,2
	sw	t3,188(sp)
	lw	t3,192(sp)
	lw	t4,188(sp)
	sub	t5,t3,t4
	sw	t5,184(sp)
	lw	t4,200(sp)
	lw	t3,184(sp)
	sw	t3,0(t4)
	j	.for.1.condition
.for.1.condition:
	lw	t3,184(sp)
	beqz	t3,.for.1.end
	j	.for.1.body
.for.1.body:
	lw	t4,200(sp)
	lw	t5,0(t4)
	sw	t5,180(sp)
	lw	t3,180(sp)
	mv	a0,t3
	call	is_prime
	mv	t5,a0
	sw	t5,176(sp)
	lw	t3,176(sp)
	beqz	t3,.if.5.end
	j	.if.5.then
.if.5.then:
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,172(sp)
	lw	t4,200(sp)
	lw	t5,0(t4)
	sw	t5,168(sp)
	lw	t3,172(sp)
	lw	t4,168(sp)
	sub	t5,t3,t4
	sw	t5,164(sp)
	lw	t4,200(sp)
	lw	t5,0(t4)
	sw	t5,160(sp)
	lw	t3,164(sp)
	mv	a0,t3
	lw	t3,160(sp)
	mv	a1,t3
	call	find
	mv	t5,a0
	sw	t5,156(sp)
	j	.find.exit
	j	.if.5.end
.if.5.else:
	j	.if.5.end
.if.5.end:
	j	.for.1.step
.for.1.step:
	lw	t4,200(sp)
	lw	t5,0(t4)
	sw	t5,152(sp)
	li	t3,1
	sw	t3,148(sp)
	lw	t3,152(sp)
	lw	t4,148(sp)
	sub	t5,t3,t4
	sw	t5,144(sp)
	lw	t4,200(sp)
	lw	t3,144(sp)
	sw	t3,0(t4)
	j	.for.1.condition
.for.1.end:
	j	.if.4.end
.if.4.else:
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,140(sp)
	li	t3,1
	sw	t3,136(sp)
	lw	t3,140(sp)
	lw	t4,136(sp)
	sub	t5,t3,t4
	sw	t5,132(sp)
	lw	t4,236(sp)
	lw	t3,132(sp)
	sw	t3,0(t4)
	j	.for.2.condition
.for.2.condition:
	lw	t3,132(sp)
	beqz	t3,.for.2.end
	j	.for.2.body
.for.2.body:
	lw	t4,236(sp)
	lw	t5,0(t4)
	sw	t5,128(sp)
	lw	t3,128(sp)
	mv	a0,t3
	call	is_prime
	mv	t5,a0
	sw	t5,124(sp)
	li	t3,0
	sw	t3,120(sp)
	lw	t3,120(sp)
	mv	t5,t3
	sw	t5,116(sp)
	lw	t3,124(sp)
	beqz	t3,.short.0.end
	j	.short.0.second
.short.0.second:
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,112(sp)
	lw	t4,236(sp)
	lw	t5,0(t4)
	sw	t5,108(sp)
	lw	t3,112(sp)
	lw	t4,108(sp)
	sub	t5,t3,t4
	sw	t5,104(sp)
	lw	t3,104(sp)
	mv	a0,t3
	call	is_prime
	mv	t5,a0
	sw	t5,100(sp)
	lw	t3,100(sp)
	mv	t5,t3
	sw	t5,116(sp)
	j	.short.0.end
.short.0.end:
	lw	t3,116(sp)
	mv	t5,t3
	sw	t5,96(sp)
	lw	t3,96(sp)
	beqz	t3,.if.6.end
	j	.if.6.then
.if.6.then:
	la	t5,string3
	sw	t5,92(sp)
	lw	t3,92(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,88(sp)
	lw	t4,332(sp)
	lw	t5,0(t4)
	sw	t5,84(sp)
	lw	t3,84(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,80(sp)
	lw	t3,80(sp)
	mv	a0,t3
	la	t5,string4
	sw	t5,76(sp)
	lw	t3,76(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,72(sp)
	lw	t4,236(sp)
	lw	t5,0(t4)
	sw	t5,68(sp)
	lw	t3,68(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,64(sp)
	lw	t3,72(sp)
	mv	a0,t3
	lw	t3,64(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,60(sp)
	lw	t3,60(sp)
	mv	a0,t3
	la	t5,string5
	sw	t5,56(sp)
	lw	t3,56(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,52(sp)
	lw	t4,340(sp)
	lw	t5,0(t4)
	sw	t5,48(sp)
	lw	t4,236(sp)
	lw	t5,0(t4)
	sw	t5,44(sp)
	lw	t3,48(sp)
	lw	t4,44(sp)
	sub	t5,t3,t4
	sw	t5,40(sp)
	lw	t3,40(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,36(sp)
	lw	t3,52(sp)
	mv	a0,t3
	lw	t3,36(sp)
	mv	a1,t3
	call	__stringConcatenate__
	mv	t5,a0
	sw	t5,32(sp)
	lw	t3,32(sp)
	mv	a0,t3
	call	__println__
	mv	t5,a0
	sw	t5,28(sp)
	j	.find.exit
	j	.if.6.end
.if.6.else:
	j	.if.6.end
.if.6.end:
	j	.for.2.step
.for.2.step:
	lw	t4,236(sp)
	lw	t5,0(t4)
	sw	t5,24(sp)
	li	t3,1
	sw	t3,20(sp)
	lw	t3,24(sp)
	lw	t4,20(sp)
	sub	t5,t3,t4
	sw	t5,16(sp)
	lw	t4,236(sp)
	lw	t3,16(sp)
	sw	t3,0(t4)
	j	.for.2.condition
.for.2.end:
	j	.if.4.end
.if.4.end:
	li	t3,0
	sw	t3,12(sp)
	lw	t3,12(sp)
	mv	a0,t3
	j	.find.exit
.find.exit:
	lw	ra,364(sp)
	addi	sp,sp,368
	jr	ra
work:
.work:
	addi	sp,sp,-32
	sw	ra,28(sp)
.work.entry:
	lw	t3,global0
	sw	t3,24(sp)
	li	t3,0
	sw	t3,20(sp)
	li	t3,1
	sw	t3,16(sp)
	lw	t3,20(sp)
	lw	t4,16(sp)
	sub	t5,t3,t4
	sw	t5,12(sp)
	lw	t3,24(sp)
	mv	a0,t3
	lw	t3,12(sp)
	mv	a1,t3
	call	find
	mv	t5,a0
	sw	t5,8(sp)
	li	t3,0
	sw	t3,4(sp)
	lw	t3,4(sp)
	mv	a0,t3
	j	.work.exit
.work.exit:
	lw	ra,28(sp)
	addi	sp,sp,32
	jr	ra
main:
.main:
	addi	sp,sp,-16
	sw	ra,12(sp)
.init_global:
	j	.main.entry
.main.entry:
	call	init
	mv	t5,a0
	sw	t5,8(sp)
	call	work
	mv	t5,a0
	sw	t5,4(sp)
	li	t3,0
	sw	t3,0(sp)
	lw	t3,0(sp)
	mv	a0,t3
	j	.main.exit
.main.exit:
	lw	ra,12(sp)
	addi	sp,sp,16
	jr	ra